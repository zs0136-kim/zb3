package com.example.springapi.send.infrastructure.repository;

import java.util.List;
import com.example.springapi.send.web.dto.SendSearchCondition;
import com.example.springapi.send.domain.model.Send;

public interface SendRepository {
    
    // 出庫情報を取得
    List<Send> selectByCondition(SendSearchCondition sendSearchCondition);
}
