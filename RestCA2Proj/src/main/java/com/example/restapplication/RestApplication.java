package com.example.restapplication;

import com.example.services.PlayListResources;
import com.example.services.RecResources;
import com.example.services.TrackResources;
import com.example.services.UserResources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {


    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public RestApplication() {

        singletons.add(new TrackResources());
        singletons.add(new PlayListResources());
        singletons.add(new RecResources());
        singletons.add(new UserResources());

    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    public void setSingletons(Set<Object> singletons) {
        this.singletons = singletons;
    }

    public Set<Class<?>> getEmpty() {
        return empty;
    }

    public void setEmpty(Set<Class<?>> empty) {
        this.empty = empty;
    }
}