package com.assessme.model;

/**
 * @author Darshan Kathiriya
 * @created 30-May-2020 11:40 PM
 */
public class Enrollment {
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
}
