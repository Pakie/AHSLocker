package com.pakie.ahslocker.parent;

import java.util.List;

public interface ParentService {
    List<Parent> findAllParents();
    Parent saveParent(Parent parent);
    Parent findParentById(Long id);
    void deleteParentById(Long id);
    //void assignParentUsername(Long id);
}
