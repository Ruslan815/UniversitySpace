package ru.ruslan.diploma.service;

import org.springframework.stereotype.Service;
import ru.ruslan.diploma.model.Attach;
import ru.ruslan.diploma.model.AttachRequest;
import ru.ruslan.diploma.model.AttachView;
import ru.ruslan.diploma.repository.AttachRepository;

import java.io.IOException;

@Service
public class AttachService {
    private final AttachRepository attachRepository;

    public AttachService(AttachRepository attachRepository) {
        this.attachRepository = attachRepository;
    }

    public AttachView create(AttachRequest attachRequest) throws IOException {
        String fileName = attachRequest.getFile().getOriginalFilename();
        String fileEnding = ".anyType";
        try {
            fileEnding = fileName.substring(fileName.lastIndexOf('.'));
        } catch (Exception ignored) {}
        String newName = attachRepository.count() + fileEnding;

        byte[] fileByteArr = attachRequest.getFile().getBytes();
        Attach attach = new Attach(attachRequest.getUserId(), attachRequest.getChatId(),
                fileByteArr, newName, attachRequest.getFile().getContentType());
        attachRepository.save(attach);

        return new AttachView(newName);
    }

    public Attach getByFileName(String fileName) {
        return attachRepository.findByFileName(fileName);
    }
}
