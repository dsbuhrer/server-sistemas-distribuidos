package com.sd.server.Packages.data.response.user;

import com.sd.server.Base.PackageData;
import com.sd.server.Models.User;

import java.util.List;

public class GetUserPackageData extends PackageData {

    private List<User> users;

    public GetUserPackageData(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
