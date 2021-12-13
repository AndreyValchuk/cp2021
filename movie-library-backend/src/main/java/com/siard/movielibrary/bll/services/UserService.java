package com.siard.movielibrary.bll.services;

import com.siard.movielibrary.bll.dtos.UserAuthDetails;
import com.siard.movielibrary.bll.dtos.UserCreationDto;
import com.siard.movielibrary.bll.exceptions.AlreadyExistsException;
import com.siard.movielibrary.bll.exceptions.NotFoundException;
import com.siard.movielibrary.dal.entities.User;
import com.siard.movielibrary.dal.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService, UserDetailsService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(SimpleGrantedAuthority::new).toList();

        return new UserAuthDetails(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public UserAuthDetails createUser(UserCreationDto userCreationDto) throws AlreadyExistsException {
        if(userRepository.existsByUsernameOrEmail(userCreationDto.getUsername(), userCreationDto.getEmail())) {
            throw new AlreadyExistsException();
        }

        User userCreationEntity = modelMapper.map(userCreationDto, User.class);
        String encodedPassword = passwordEncoder.encode(userCreationEntity.getPassword());
        userCreationEntity.setPassword(encodedPassword);
        userCreationEntity.getRoles().add("ROLE_USER");

        User createdUser = userRepository.save(userCreationEntity);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserAuthDetails(createdUser.getId(), createdUser.getUsername(), createdUser.getEmail(), null, authorities);
    }

    @Override
    public Collection<String> getUserRoles(String username) throws NotFoundException {
        User user = userRepository.getByUsername(username).orElseThrow(NotFoundException::new);

        return user.getRoles();
    }

    @Override
    public void updateUserRoles(String username, Collection<String> roles) throws NotFoundException {
        User user = userRepository.getByUsername(username).orElseThrow(NotFoundException::new);

        user.setRoles(roles);
    }
}