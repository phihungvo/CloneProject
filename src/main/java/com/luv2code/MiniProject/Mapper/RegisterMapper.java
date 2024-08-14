package com.luv2code.MiniProject.Mapper;

import com.luv2code.MiniProject.entity.User;
import com.luv2code.MiniProject.payload.RegisterDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper extends CommonMapper<User, RegisterDto>{

}
