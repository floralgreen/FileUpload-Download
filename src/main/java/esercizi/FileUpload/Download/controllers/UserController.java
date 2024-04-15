package esercizi.FileUpload.Download.controllers;

import esercizi.FileUpload.Download.entities.DTO.DownloadUserProfilePictureDTO;
import esercizi.FileUpload.Download.entities.User;
import esercizi.FileUpload.Download.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(service.createUser(user));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getFromId(@PathVariable Long id) {
        Optional<User> userOptional = service.getUserFromId(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = service.updateUser(id, user);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteFromId(@PathVariable Long id) {
        Optional<User> userOptional = service.deleteUserFromId(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/upload/profilepicture/{id}")
    public ResponseEntity<User> uploadProfilePicture(@PathVariable Long id, @RequestParam MultipartFile picture) throws IOException {
        Optional<User> userOptional = service.uploadProfilePicture(id, picture);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/download/profilepicture/{id}")
    public ResponseEntity<DownloadUserProfilePictureDTO> downloadProfilePicture(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<DownloadUserProfilePictureDTO> profilePictureDTOOptional = service.downloadProfilePicture(id, response);
        if (profilePictureDTOOptional.isPresent()) {
            return ResponseEntity.ok(profilePictureDTOOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
