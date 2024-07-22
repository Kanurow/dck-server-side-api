package com.rowland.engineering.dck.service;


import com.rowland.engineering.dck.dto.RoleResponse;
import com.rowland.engineering.dck.model.Role;
import com.rowland.engineering.dck.model.User;

import java.util.List;
import java.util.UUID;


public interface IRoleService {
    List<RoleResponse> getRoles();

}
