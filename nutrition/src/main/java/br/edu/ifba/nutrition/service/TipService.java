package br.edu.ifba.nutrition.service;

import java.util.List;
import java.util.Optional;

import br.edu.ifba.nutrition.domain.dto.request.TipRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.TipResponseWithoutCommentDto;
import br.edu.ifba.nutrition.entity.Tip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifba.nutrition.domain.dto.response.TipResponseWithCommentDto;
import br.edu.ifba.nutrition.repository.TipRepository;

@Service
public class TipService {
    
    @Autowired
    private TipRepository tipRepository;

    public TipResponseWithoutCommentDto save(TipRequestDto data){
        var dataEntity = data.toEntity();
        return TipResponseWithoutCommentDto.toDto(tipRepository.save(dataEntity)) ;
    }

    public Optional<List<TipResponseWithCommentDto>> find(String title, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        if(title == null) return Optional.of(TipResponseWithCommentDto.toListDto(tipRepository.findAll(pageable).toList()));
        return Optional.of(TipResponseWithCommentDto.toListDto(tipRepository.findByTitleContains(title, pageable)));
    }

    public Optional<TipResponseWithCommentDto> findById(Long id){
        return tipRepository.findById(id).map(TipResponseWithCommentDto::new);
    }

    public TipResponseWithoutCommentDto update(Long id, TipRequestDto entity){
        var data = entity.toEntity();
        data.setId(id);
        return TipResponseWithoutCommentDto.toDto(tipRepository.save(data));
    }

    public void deleteById(Long id){
        tipRepository.deleteById(id);
    }
}
