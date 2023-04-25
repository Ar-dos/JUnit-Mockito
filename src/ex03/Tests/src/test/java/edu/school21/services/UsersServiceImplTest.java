package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class UsersServiceImplTest {

    private org.mockito.Mockito Mockito;

    @Test
    void exceptionTest() throws AlreadyAuthenticatedException {
        UsersServiceImpl usersService = Mockito.mock(UsersServiceImpl.class);
        Mockito.when(usersService.findByLogin("user")).thenReturn(new User(1l,"user", "password",true));
        Mockito.when(usersService.authenticate("user","password")).thenCallRealMethod();
        assertThrows(AlreadyAuthenticatedException.class,  () -> { usersService.authenticate("user","password");});
    }

    @Test
    void correctLoginPasswordTest() throws AlreadyAuthenticatedException {
        UsersServiceImpl usersService = Mockito.mock(UsersServiceImpl.class);
        Mockito.when(usersService.findByLogin("user")).thenReturn(new User(1l,"user", "password",false));
        Mockito.when(usersService.authenticate("user","password")).thenCallRealMethod();
        assertTrue(usersService.authenticate("user","password"));
        assertTrue(usersService.findByLogin("user").isAuthentication());
        User user = usersService.findByLogin("user");
        user.setAuthentication(true);
        Mockito.verify(usersService).update(user);
    }

    @Test
    void wrongLoginTest() throws AlreadyAuthenticatedException {
        UsersServiceImpl usersService = Mockito.mock(UsersServiceImpl.class);
        Mockito.when(usersService.findByLogin("user")).thenThrow( new EntityNotFoundException());
        Mockito.when(usersService.authenticate("user","password")).thenCallRealMethod();
        assertThrows(EntityNotFoundException.class,  () -> { usersService.authenticate("user","password");});
    }

    @Test
    void wrongPasswordTest() throws AlreadyAuthenticatedException {
        UsersServiceImpl usersService = Mockito.mock(UsersServiceImpl.class);
        Mockito.when(usersService.findByLogin("user")).thenReturn(new User(1l,"user", "wrong",false));
        Mockito.when(usersService.authenticate("user","password")).thenCallRealMethod();
        assertFalse(usersService.authenticate("user","password"));
        assertFalse(usersService.findByLogin("user").isAuthentication());
    }

}
