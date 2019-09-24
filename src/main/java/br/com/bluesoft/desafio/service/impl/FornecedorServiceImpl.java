package br.com.bluesoft.desafio.service.impl;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.NovoPedidoDTO;
import br.com.bluesoft.desafio.service.FornecedorService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Override
    public List<FornecedorDTO> obterFornecedores(String gtin) {
        String url = "https://egf1amcv33.execute-api.us-east-1.amazonaws.com/dev/produto/" + gtin;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        List<FornecedorDTO> fornecedors = null;
        try {
            Response response = client.newCall(request).execute();
            fornecedors = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<FornecedorDTO>>() {
            });
        } catch (IOException ignored) {
        }
        return fornecedors;
    }
}
