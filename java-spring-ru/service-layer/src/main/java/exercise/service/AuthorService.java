package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getALL() {
        return authorRepository.findAll().stream()
            .map(author -> authorMapper.map(author))
            .toList();
    }

    public AuthorDTO findById(Long id) {
        var author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));

        return authorMapper.map(author);
    }

    public AuthorDTO create(AuthorCreateDTO authorDto) {
        var author = authorMapper.map(authorDto);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public AuthorDTO update(AuthorUpdateDTO authorDto, Long id) {
        var author = authorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));

        authorMapper.update(authorDto, author);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
    // END
}
