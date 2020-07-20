package org.moeftc.fastcodeloader;

public class TeamLessClassLoader extends ClassLoader {
    public TeamLessClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("org.firstinspires.ftc.teamcode")) {
            throw new ClassNotFoundException();
        }
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.startsWith("org.firstinspires.ftc.teamcode")) {
            throw new ClassNotFoundException();
        }
        return super.findClass(name);
    }

}
