package com.isechome.ecommerce.mapper.isechome;

import com.isechome.ecommerce.entity.PieceWeight;
import java.util.List;

public interface PieceWeightMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PieceWeight record);

    PieceWeight selectByPrimaryKey(Integer id);

    List<PieceWeight> selectAll();

    int updateByPrimaryKey(PieceWeight record);

    void review();

    PieceWeight getBySpec(String spec);
}