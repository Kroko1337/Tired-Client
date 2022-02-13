package beta.tiredb56.module;


import beta.tiredb56.api.annotations.ModuleAnnotation;
import beta.tiredb56.tired.CheatMain;
import org.reflections.Reflections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ModuleManager {

    private final List<Module> moduleList = new ArrayList<>();
    public static ArrayList<Module> sortedModList = new ArrayList<>();

    public ModuleManager() {
        final Reflections reflections = new Reflections("beta.tiredb56");
        final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ModuleAnnotation.class);
        for (Class<?> aClass : classes) {
            try {
                final Module module = (Module) aClass.newInstance();
                if (!module.equals(CheatMain.INSTANCE.bootups.getNotAllowedBootModules())) {
                    moduleList.add(module);
                } else {
                    System.out.println(module.getName());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sortedModList.addAll(moduleList);

    }

    public static <T extends Module> T getInstance(Class<T> tClass) {
        return CheatMain.INSTANCE.moduleManager.moduleBy(tClass);
    }

    public final ArrayList<Module> getModules(ModuleCategory category) {
        ArrayList<Module> modules = new ArrayList<>();
        for (Module m : this.moduleList) {
            if (m.getModuleCategory() == category)
                modules.add(m);
        }
        return modules;
    }

    public <T extends Module> T moduleBy(Class<T> tClass) {
        return (T) moduleList.stream().filter(mod -> mod.getClass().equals(tClass)).findFirst().orElse(null);
    }


    public Module findModuleByClass(Class clazz) {
        for (Module module : moduleList) {
            if (module.getClass().equals(clazz)) {
                return module;
            }
        }
        return null;
    }

    public Module moduleBy(String name) {
        for (Module module : moduleList) {
            if (module.getName().equalsIgnoreCase(name)) return module;
        }
        return null;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void addModule(Module... modules) {
        moduleList.addAll(Arrays.asList(modules));
    }

}
