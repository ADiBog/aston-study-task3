package ServletTest;

import com.example.firstwebapp.entity.Beer;
import com.example.firstwebapp.service.BeerService;
import com.example.firstwebapp.servlet.BeerServlet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.TestConProv;

import javax.servlet.ServletException;
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

public class BeerServletTest {
    private static final String H2_CONNECTION_STRING = "jdbc:h2:mem:test";
    static Connection connection;
    BeerService beerService = new BeerService(new TestConProv());
    private static String getFileContent() throws IOException {
        try (var inputStream = BeerServletTest.class.getClassLoader().getResourceAsStream("seed.sql")) {
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
        when(request.getParameter("name")).thenReturn("Lis");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new BeerServlet(beerService).doPost(request, response);
        ArrayList<Beer> beers = beerService.select();
        Assertions.assertThat(beers).hasSize(beerService.select().size());
    }

    @Test
    public void testServletDel() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("beer_id")).thenReturn("1");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        new BeerServlet(beerService).doDelete(request, response);
        ArrayList<Beer> beers = beerService.select();
        Assertions.assertThat(beers).hasSize(beerService.select().size());
    }
}
