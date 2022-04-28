package vuce2.mfeconfig.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class ModuleConfig {
    @Id
    private ObjectId id;
    private String remoteEntry;
    private String remoteName;
    private String exposedModule;
    private String displayName;
    private String routePath;
    private String ngName;
    private String[] components;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getRemoteEntry() {
        return remoteEntry;
    }

    public void setRemoteEntry(String remoteEntry) {
        this.remoteEntry = remoteEntry;
    }

    public String getRemoteName() {
        return remoteName;
    }

    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName;
    }

    public String getExposedModule() {
        return exposedModule;
    }

    public void setExposedModule(String exposedModule) {
        this.exposedModule = exposedModule;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public String getNgName() {
        return ngName;
    }

    public void setNgName(String ngName) {
        this.ngName = ngName;
    }

    public String[] getComponents() {
        return components;
    }

    public void setComponents(String[] components) {
        this.components = components;
    }
}
