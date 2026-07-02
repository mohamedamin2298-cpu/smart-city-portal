package project.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.backend.dto.ProfileResponseDTO;
import project.backend.dto.UserRequestDTO;
import project.backend.dto.UserResponseDTO;
import project.backend.entity.User;
import project.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserResponseDTO createUser(UserRequestDTO dto){
        Optional<User> existingUser = userRepo.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user=new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        userRepo.save(user);
        return mapToResponse(user);
    }
    public List<UserResponseDTO> getAllUsers(){
     return userRepo.findAll()
               .stream()
               .map(this::mapToResponse)
               .collect(Collectors.toList());

    }
    public UserResponseDTO getUserById(long id){
       User user=userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(user);
    }
    public void delete(long id){
        userRepo.deleteById(id);
    }
    public UserResponseDTO update(long id,UserRequestDTO dto){
        User user=userRepo.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));
        if (!user.getEmail().equals(dto.getEmail())){
            if (userRepo.findByEmail(dto.getEmail()).isPresent()){
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());
        userRepo.save(user);
        return mapToResponse(user);
    }
    private UserResponseDTO mapToResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }
    public ProfileResponseDTO profile(){
        String email= SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new ProfileResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
