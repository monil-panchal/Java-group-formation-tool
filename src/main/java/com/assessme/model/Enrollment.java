package com.assessme.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:40 PM
 */
public class Enrollment implements Serializable {

    private Long userId;
    private Integer roleId;
    private Long courseId;

    public Enrollment(Long userId, Integer roleId, Long courseId) {
        this.userId = userId;
        this.roleId = roleId;
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
            "userId=" + userId +
            ", roleId=" + roleId +
            ", courseId=" + courseId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enrollment that = (Enrollment) o;
        return userId.equals(that.userId) &&
            roleId.equals(that.roleId) &&
            courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId, courseId);
    }
}
