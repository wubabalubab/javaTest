package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseNode {

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

    public void getNodeForId(int id) {
        if (this.id == id) {
            System.out.println(this);
        }
        if (childList != null && childList.size() > 0) {
            for (BaseNode node : childList) {
                 node.getNodeForId(id);
            }
        }
    }
    public void soutall(){
        System.out.println(this);
        if (childList != null &&childList.size()>0) {
            for (BaseNode node : childList) {
                node.soutall();
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
                ", version='" + version + '\'' +
                ", childList=" + childList +
                ", isLastList=" + isLastList +
                '}';

    }
}
