package org;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {
    @NotBlank(message="name is requered")
    private String name;
    @NotNull
    private Integer age;

    public CreateUserRequest() {
        this.age = age;
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
