package com.example.springapi.send.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.example.springapi.send.infrastructure.repository.SendEntity;
import com.example.springapi.send.web.dto.SendSearchCondition;

@Mapper
public interface SendMapper {
    
    // 出庫情報を取得
    List<SendEntity> selectByCondition(SendSearchCondition sendSearchCondition);
}
