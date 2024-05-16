package com.sd.server.Packages.data.response.user;

import com.sd.server.Base.PackageData;
import com.sd.server.Models.User;

public class FindUserPackageData extends PackageData {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FindUserPackageData() {
    }

    public FindUserPackageData(User user) {
        this.user = user;
    }
}
