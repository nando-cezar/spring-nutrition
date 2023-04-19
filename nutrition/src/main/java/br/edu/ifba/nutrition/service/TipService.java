package br.edu.ifba.nutrition.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.nutrition.domain.dto.request.TipRequestDto;
import br.edu.ifba.nutrition.domain.dto.response.TipResponseDto;
import br.edu.ifba.nutrition.repository.TipRepository;

@Service
public class TipService {
    
    @Autowired
    private TipRepository tipRepository;

    public TipResponseDto save(TipRequestDto data){
        var dataEntity = data.toEntity();
        return TipResponseDto.toDto(tipRepository.save(dataEntity)) ;
    }

    public Optional<List<TipResponseDto>> find(String title){
        if(title == null) return Optional.of(TipResponseDto.toListDto(tipRepository.findAll()));
        return Optional.of(TipResponseDto.toListDto(tipRepository.findByTitleContains(title)));
    }

    public Optional<TipResponseDto> findById(Long id){
        return tipRepository.findById(id).map(TipResponseDto::new);
    }

    public TipResponseDto update(Long id, TipRequestDto entity){
        var data = entity.toEntity();
        data.setId(id);
        return TipResponseDto.toDto(tipRepository.save(data));
    }

    public void deleteById(Long id){
        tipRepository.deleteById(id);
    }
}
