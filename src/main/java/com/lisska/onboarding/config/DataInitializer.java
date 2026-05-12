package com.lisska.onboarding.config;

import com.lisska.onboarding.model.Group;
import com.lisska.onboarding.model.Task;
import com.lisska.onboarding.model.User;
import com.lisska.onboarding.model.enums.Role;
import com.lisska.onboarding.repository.GroupRepository;
import com.lisska.onboarding.repository.TaskRepository;
import com.lisska.onboarding.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, GroupRepository groupRepository,
                           TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Если в базе нет пользователей — создаем тестовый набор
        if (userRepository.count() == 0) {
            System.out.println("--- ПЕРВИЧНАЯ ИНИЦИАЛИЗАЦИЯ ТЕСТОВЫХ ПОЛЬЗОВАТЕЛЕЙ ---");

            // 1. Создаем группу и задачу (общие данные)
            Group devGroup = new Group();
            devGroup.setName("Backend Developers");
            groupRepository.save(devGroup);

            Task welcomeTask = new Task();
            welcomeTask.setTitle("Добро пожаловать!");
            welcomeTask.setDescription("Прочитай гайд и заполни профиль.");
            taskRepository.save(welcomeTask);

            // 2. Создаем АДМИНИСТРАТОРА
            createUser("admin@test.com", "admin123", "Виктория Админ", Role.ADMIN, devGroup);

            // 3. Создаем МЕНТОРА
            createUser("mentor@test.com", "mentor123", "Иван Наставник", Role.MENTOR, devGroup);

            // 4. Создаем ОБЫЧНОГО ПОЛЬЗОВАТЕЛЯ
            createUser("user@test.com", "user123", "Алексей Новичок", Role.USER, devGroup);

            System.out.println("--- ВСЕ ТЕСТОВЫЕ РОЛИ (ADMIN, MENTOR, USER) СОЗДАНЫ ---");
        } else {
            System.out.println("--- БАЗА УЖЕ СОДЕРЖИТ ДАННЫЕ. ПРОПУСК ИНИЦИАЛИЗАЦИИ ---");
        }
    }

    // Вспомогательный метод, чтобы не дублировать код создания пользователя
    private void createUser(String email, String pass, String name, Role role, Group group) {
        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(pass));
        user.setFullName(name);
        user.setRole(role);
        user.setGroup(group);
        userRepository.save(user);
    }
}