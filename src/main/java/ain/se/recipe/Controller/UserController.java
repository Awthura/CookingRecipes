package ain.se.recipe.Controller;
import ain.se.recipe.models.User;
import ain.se.recipe.Service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/signup2", method = RequestMethod.GET)    
    public ModelAndView createNewUser2(@RequestParam(value="email") String email,@RequestParam(value="password") String password,@RequestParam(value="fullname") String fullname) {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        
        user.setEmail(email);
        user.setPassword(password);
        user.setFullname(fullname);

            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody // Add this annotation to indicate that the response should be serialized as JSON
    public Map<String, String> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        Map<String, String> response = new HashMap<>();

        // Find the user by email
        User user = userService.findUserByEmail(email);
        
        if (user != null) {
            // Check if the password matches
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                // Login successful
                response.put("message", "Login successful");
                response.put("outputMessage", "success");
            } else {
                // Login failed - invalid password
                response.put("message", "Incorrect password");
                response.put("outputMessage", "fail");
            }
        } else {
            // Login failed - user not found
            response.put("message", "Invalid email");
            response.put("outputMessage", "no user");
        }
        
        return response;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> createNewUser(@RequestParam(value = "email") String email,
                                            @RequestParam(value = "password") String password,
                                            @RequestParam(value = "fullname") String fullname) {
        Map<String, Object> response = new HashMap<>();

        User existingUser = userService.findUserByEmail(email);

        if (existingUser != null) {
            // User already exists
            response.put("message", "User already exists");
            response.put("outputMessage", "user exists");
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setFullname(fullname);

            userService.saveUser(newUser);

            response.put("message", "User registered successfully");
            response.put("outputMessage", "success");
        }

        return response;
    }
    
}
