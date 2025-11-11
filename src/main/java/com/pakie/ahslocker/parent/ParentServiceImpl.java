package com.pakie.ahslocker.parent;

import com.pakie.ahslocker.student.StudentRepo;
import com.pakie.ahslocker.user.User;
import com.pakie.ahslocker.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepo parentRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<Parent> findAllParents() {
        return parentRepo.findAll();
    }

    @Override
    public Parent saveParent(Parent parent) {
        this.parentRepo.save(parent);
        return parent;
    }

    @Override
    public Parent findParentById(Long id) {
        Optional<Parent> optional = parentRepo.findById(id);
        Parent parent = null;
        if (optional.isPresent()) {
            parent = optional.get();
        } else {
            throw new RuntimeException("Parent with id " + id + " not found");
        }
        return parent;
    }

    @Override
    public void deleteParentById(Long id) {
        this.parentRepo.deleteById(id);
    }

    /*@Override
    public void assignParentUsername(Long id) {
        Parent parent = parentRepo.findById(id).orElse(null);
        assert parent != null;
        User user = userRepo.findUserByUsername(parent.getParentEmailAddress());
        parent.setUsername(user.getUsername());
        parentRepo.save(parent);
    }*/
}
