package com.trail.musicalhost.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trail.musicalhost.model.Music;
import com.trail.musicalhost.model.ResponseFile;
import com.trail.musicalhost.model.User;
import com.trail.musicalhost.model.detail;
import com.trail.musicalhost.service.MusicService;
import com.trail.musicalhost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.*;
import java.util.stream.Collectors;
@RestController
public class MusicController {
    @Autowired
    MusicService musicService;
    @Autowired
    UserService userService;

    //-------------------delete by music Id
    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable int id)
    {
           this.musicService.deleteMusic(id);
        String response = "{\"success\" : true, \"message\" : \"Post Deleted Successfully\"}";
        return response;
    }
    //---------------------------------post data
    @RequestMapping("/post")
    public List<Music> getAllPost()
    {
        return this.musicService.getAllMusic();
    }
// get data by music Id
    @RequestMapping("/getpostbyid/{id}")
    public Music getPost(@PathVariable int id)
    {
        return this.musicService.getMusic(id);
    }
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = musicService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(dbFile.getMusicId()))
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getDescription(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    //---------------all user Post
    @RequestMapping("/getPostByUserId")
    public List<Music> getPostByUserId(){
        User user = userService.getCurrentLoggedINUser();
        return  this.musicService.getPostByUserID(user.getId());
    }

    //----------------------upload data
    @PostMapping(value = "/upload")
    public void uploadFile(@RequestPart(value = "file",required=true) MultipartFile file,@RequestPart(value = "title",required=true) String  title,@RequestPart(value = "desc",required=true) String  desc)
    {
        String message = "";
        try {
        Music data=new Music();
        data.setName(title);
            data.setDescription(desc);
            musicService.store(file,data);
            System.out.println("Uploaded the file successfully: " + file.getOriginalFilename());
        }
        catch (Exception e) {
           System.out.println(e);
        }
    }
    //--------------------getBymusicid
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable int  id) {
        Music fileDB = musicService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


//---------------------Change music file
    @RequestMapping(method = RequestMethod.PUT, value = "/post/update/{id}")
    public String updatePost(@PathVariable int id, @RequestParam("file") MultipartFile file)
    {
        String response ="";
        try {
            if (this.musicService.updateMusic(id, file)) {
                response = "{\"success\" : true, \"message\" : \"Post updated Successfully\"}";
            } else {
                response = "{\"success\" : true, \"message\" : \"Post Cannot be updated\"}";
            }
        }
        catch(Exception e)
        {
            System.out.println("error ");
        }
        return response;

    }


}
