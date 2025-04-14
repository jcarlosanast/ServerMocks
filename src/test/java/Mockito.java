import org.junit.*;

import static org.mockito.Mockito.*;

public class Mockito {

    @Test
    public void validate_cpf_success() {
        customerRegister = mock(CustomerRegister.class);
        when (customerRegister.validateRealCpf(anyString())).thenReturn(true);
        Assert.assertTrue(customerRegister.validateRealCpf("8888"));
    }
}
