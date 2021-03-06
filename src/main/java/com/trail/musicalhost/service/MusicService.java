package com.trail.musicalhost.service;
import com.trail.musicalhost.model.Music;
import com.trail.musicalhost.model.User;
import com.trail.musicalhost.model.detail;
import com.trail.musicalhost.repository.MusicRepository;
import com.trail.musicalhost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.trail.musicalhost.model.User.SEQUENCE_NAME;

@Service
public class MusicService {

    @Autowired
    private SequenceGeneratorService service;
    @Autowired
    public MusicRepository musicRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    //--------------------store data
    public void store(MultipartFile file, Music details) throws IOException
    {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Music FileDB = new Music(fileName, file.getContentType(), file.getBytes());
        User user = userService.getCurrentLoggedINUser();
        FileDB.setUser(user);
        FileDB.setDescription(details.getDescription());
        FileDB.setTag(details.getName());
        FileDB.setMusicId(service.getSequenceNumber(SEQUENCE_NAME));
        musicRepository.save(FileDB);
        userRepository.delete(user);
        user.getMusic().add(FileDB);
        userRepository.save(user);
    }

    public Music getFile(int id) {
        return musicRepository.findById(id).get();
    }
    public Stream<Music> getAllFiles() {
        return musicRepository.findAll().stream();
    }

    public List<Music> getAllMusic(){
        return (List<Music>) musicRepository.findAll();
    }

    public Music getMusic(int id){
        Optional<Music> music = musicRepository.findById(id);
        if(music.isPresent())
        {
            return music.get();
        }
        else
            {
            return null;
        }
    }
    @Transactional
    public List<Music> getPostByUserID(Integer id){
        return this.musicRepository.findAllMusicByUserId(id);
    }
    public void deleteMusic(int   id){
        this.musicRepository.deleteById(id);
    }
    // Method to add a post
    public void addMusic(Music music){
        this.musicRepository.save(music);
    }
    public boolean updateMusic(int id,  MultipartFile file)throws IOException
    {
        User user = userService.getCurrentLoggedINUser();

        if(this.musicRepository.existsById(id)){
            Optional<Music> existMusic = musicRepository.findById(id);
            Music music1 = existMusic.get();

            //----------------------------------------------------------------------------------
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Music FileDB = new Music(fileName, file.getContentType(), file.getBytes());

            FileDB.setMusicId(music1.getMusicId());
            FileDB.setUser(user);
             musicRepository.save(FileDB);
            return true;
        }else {
            System.out.println("not found");
            return false;
        }
    }

}
