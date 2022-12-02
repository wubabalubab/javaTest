package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseNode {

    static boolean returned = false;
    private int id;
    private String name;
    private String type;
    private String path;
    private BaseNode parentNode;
    private String version;
    private List<BaseNode> childList;
    private boolean isLastList;

    public BaseNode() {
    }

    public BaseNode(int id, String name, String path, BaseNode parentNode, String version) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.parentNode = parentNode;
        this.version = version;
    }

    public BaseNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(BaseNode parentNode) {
        this.parentNode = parentNode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLastList() {
        return isLastList;
    }

    public void setLastList(boolean lastList) {
        isLastList = lastList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<BaseNode> getChildList() {
        return childList;
    }

    public void setChildList(List<BaseNode> childList) {
        this.childList = childList;
    }

    public void setChildBean(BaseNode baseNode) {
        if (childList == null) {
            childList = new ArrayList<>();
        }
        childList.add(baseNode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseNode)) return false;
        BaseNode baseNode = (BaseNode) o;
        return Objects.equals(getName(), baseNode.getName()) && Objects.equals(getType(), baseNode.getType())
                && Objects.equals(getPath(), baseNode.getPath()) && Objects.equals(getVersion(), baseNode.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getPath(), getVersion());
    }

    public void printForName(String name) {
        if (returned) {
            return;
        }
        if (this.name != null && this.name.length() > 0) {
            if (this.name.equals(name)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println(gson.toJson(this));
                returned = true;
                return;
            }
        }
        if (this.childList != null && this.childList.size() > 0) {
            for (BaseNode node : childList) {
                node.printForName(name);
            }
        }
    }

    public void getChildList(List<String> targetList) {

        if (this.name != null && this.name.length() > 0) {
            if (targetList.contains(this.name)) {
                System.out.println(this.name);
                if (this.getChildList() != null && this.getChildList().size() > 0) {
                    for (BaseNode baseNode : this.getChildList()) {
                        System.out.println(">>>> " + baseNode.getPath() + ":" + baseNode.getName() + ":" + baseNode.getVersion());
                    }
                }
                System.out.println("");
                targetList.remove(name);
            }
        }
        if (this.childList != null && this.childList.size() > 0) {
            for (BaseNode node : childList) {
                node.getChildList(targetList);
            }
        }
    }


    @Override
    public String toString() {
        return "BaseNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", parentNode=" + parentNode +
                ", version='" + version + '\'' +
                ", childList=" + childList +
                ", isLastList=" + isLastList +
                '}';
    }
}
