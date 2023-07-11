package ServletTest;

import com.example.firstwebapp.entity.Beer;
import com.example.firstwebapp.entity.Distributor;
import com.example.firstwebapp.service.BeerService;
import com.example.firstwebapp.service.DistributorService;
import com.example.firstwebapp.servlet.BeerServlet;

import com.example.firstwebapp.servlet.DistributorServlet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.TestConProv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class DistributorServletTest {
    private static final String H2_CONNECTION_STRING = "jdbc:h2:mem:test";
    static Connection connection;
    DistributorService distributorService = new DistributorService(new TestConProv());
    private static String getFileContent() throws IOException {
        try (var inputStream = DistributorServletTest.class.getClassLoader().getResourceAsStream("seed.sql")) {
            if (inputStream == null) {
                return "";
            }
            return new String(inputStream.readAllBytes());
        }
    }

    @BeforeAll
    public static void init() throws SQLException, IOException {
        connection = DriverManager.getConnection(H2_CONNECTION_STRING);
        try (PreparedStatement preparedStatement = connection.prepareStatement(getFileContent())) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    public void testServletPost() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("name")).thenReturn("SPbeer");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new DistributorServlet(distributorService).doPost(request, response);
        ArrayList<Distributor> distributors = distributorService.select();
        Assertions.assertThat(distributors).hasSize(distributorService.select().size());
    }

    @Test
    public void testServletDel() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("distributor_id")).thenReturn("1");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new DistributorServlet(distributorService).doDelete(request, response);
        ArrayList<Distributor> distributors = distributorService.select();
        Assertions.assertThat(distributors).hasSize(distributorService.select().size());
    }
}
