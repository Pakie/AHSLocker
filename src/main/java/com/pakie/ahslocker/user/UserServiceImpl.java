package com.pakie.ahslocker.user;

import com.pakie.ahslocker.parent.Parent;
import com.pakie.ahslocker.parent.ParentRepo;
import com.pakie.ahslocker.role.Role;
import com.pakie.ahslocker.role.RoleRepo;
import com.pakie.ahslocker.student.Student;
import com.pakie.ahslocker.student.StudentRepo;
import com.pakie.ahslocker.sysadmin.SysAdmin;
import com.pakie.ahslocker.sysadmin.SysAdminRepo;
import com.pakie.ahslocker.web.UserDto;
import com.pakie.ahslocker.web.UserRegistrationDto;
import com.pakie.ahslocker.web.custom_errors.UserAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public RoleRepo roleRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ParentRepo parentRepo;

    @Autowired
    private SysAdminRepo sysAdminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public void encodePassword(UserDto source, User target) {
        target.setPassword(passwordEncoder.encode(source.getPassword()));
    }

    @Override
    @Transactional
    public User register(UserRegistrationDto registrationDto) {
        String email = registrationDto.getEmail();
        String rawPassword = registrationDto.getPassword();
        String roleInput = registrationDto.getRole();

        if (email == null || roleInput == null || rawPassword == null) {
            throw new IllegalArgumentException("Email, password and role are required");
        }

        // Prevent duplicate registration
        User existing = userRepo.findUserByUsername(email);
        if (existing != null) {
            throw new UserAlreadyExists();
        }

        String normalizedRole = roleInput.trim().toUpperCase();
        String roleName;
        switch (normalizedRole) {
            case "STUDENT":
                roleName = "ROLE_STUDENT";
                break;
            case "PARENT":
                roleName = "ROLE_PARENT";
                break;
            case "ADMIN":
            case "SYSADMIN":
                roleName = "ROLE_ADMIN";
                break;
            default:
                throw new IllegalArgumentException("Unsupported role: " + roleInput);
        }

        Role role = roleRepo.findByName(roleName);
        if (role == null) {
            throw new IllegalStateException("Role not configured: " + roleName);
        }

        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        // Eligibility checks and linking to domain profile
        switch (normalizedRole) {
            case "STUDENT": {
                Long studNo = registrationDto.getStudentNumber();
                if (studNo == null) {
                    throw new IllegalArgumentException("Student number is required for STUDENT registration");
                }
                Student studentByEmail = studentRepo.findByStudentEmail(email);
                if (studentByEmail == null) {
                    throw new IllegalArgumentException("No student profile found with email: " + email);
                }
                if (!studNo.equals(studentByEmail.getStudentNumber())) {
                    throw new IllegalArgumentException("Student number does not match our records");
                }
                // Link both sides
                studentByEmail.setUser(user);
                user.setStudent(studentByEmail);
                break;
            }
            case "PARENT": {
                Long idNum = registrationDto.getIdNumber();
                if (idNum == null) {
                    throw new IllegalArgumentException("ID number is required for PARENT registration");
                }
                Parent parentByEmail = parentRepo.findByParentEmailAddress(email);
                if (parentByEmail == null) {
                    throw new IllegalArgumentException("No parent profile found with email: " + email);
                }
                if (!idNum.equals(parentByEmail.getParentIdNumber())) {
                    throw new IllegalArgumentException("ID number does not match our records");
                }
                parentByEmail.setUser(user);
                user.setParent(parentByEmail);
                break;
            }
            case "ADMIN":
            case "SYSADMIN": {
                Long idNum = registrationDto.getIdNumber();
                if (idNum == null) {
                    throw new IllegalArgumentException("ID number is required for ADMIN registration");
                }
                SysAdmin adminByEmail = sysAdminRepo.findByEmail(email);
                if (adminByEmail == null) {
                    throw new IllegalArgumentException("No admin profile found with email: " + email);
                }
                if (!idNum.equals(adminByEmail.getIdNumber())) {
                    throw new IllegalArgumentException("ID number does not match our records");
                }
                adminByEmail.setUser(user);
                user.setSysAdmin(adminByEmail);
                break;
            }
        }

        // Save user (and linked profile via cascade/owning side update)
        User saved = userRepo.save(user);
        return saved;
    }
}
