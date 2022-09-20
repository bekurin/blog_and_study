package com.example.core.a_kotlinBasic.klass;

public class JavaClass {
    private final String name;
    private Boolean option;

    public JavaClass(String name, Boolean option) {
        this.name = name;
        this.option = option;
    }

    public String getName() {
        return name;
    }

    public Boolean getOption() {
        return option;
    }

    public void setOption(Boolean option) {
        this.option = option;
    }
}
