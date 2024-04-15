package esercizi.FileUpload.Download.services;

import esercizi.FileUpload.Download.entities.DTO.DownloadUserProfilePictureDTO;
import esercizi.FileUpload.Download.entities.User;
import esercizi.FileUpload.Download.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private FileStorageService fileStorageService;

    public User createUser(User user) {
        return repository.save(user);
    }

    public List<User> getAllUser() {
        return repository.findAll();
    }

    public Optional<User> getUserFromId(Long id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> updateUser(Long id, User user) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            userOptional.get().setName(user.getName());
            userOptional.get().setProfilePicture(user.getProfilePicture());
            User userSaved = repository.save(userOptional.get());
            return Optional.of(userSaved);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> deleteUserFromId(Long id) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            repository.deleteById(id);
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> uploadProfilePicture(Long id, MultipartFile picture) throws IOException {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            String pictureName = fileStorageService.upload(picture);
            userOptional.get().setProfilePicture(pictureName);
            User userUpdated = repository.save(userOptional.get());
            return Optional.of(userUpdated);
        } else {
            return Optional.empty();
        }
    }

    public Optional<DownloadUserProfilePictureDTO> downloadProfilePicture(Long id, HttpServletResponse response) throws IOException {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {

            String fileName = userOptional.get().getProfilePicture();
            byte[] profilePicture = fileStorageService.download(fileName, response);
            DownloadUserProfilePictureDTO userProfilePictureDTO = new DownloadUserProfilePictureDTO();
            userProfilePictureDTO.setUser(userOptional.get());
            userProfilePictureDTO.setProfilePicture(profilePicture);
            return Optional.of(userProfilePictureDTO);

        } else {
            return Optional.empty();
        }
    }
}