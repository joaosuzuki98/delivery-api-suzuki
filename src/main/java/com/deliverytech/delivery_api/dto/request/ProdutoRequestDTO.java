package com.deliverytech.delivery_api.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "DTO para requisição de produtos",
        title = "Produto Request DTO")
public class ProdutoRequestDTO {

    @Schema(description = "Nome do produto", example = "Pizza Margherita", required = true)
    @NotNull(message = "O Nome do produto é obrigatório")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Deliciosa pizza com molho de tomate, mussarela e manjericão", required = true)
    @NotNull(message = "A descrição do produto é obrigatória")
    private String descricao;

    @Schema(description = "Preço do produto", example = "29.90", required = true)
    @NotNull(message = "O preço do produto é obrigatório")
    private BigDecimal preco;

    @Schema(description = "Categoria do produto", example = "Pizzas", required = true)
    @NotNull(message = "A categoria do produto é obrigatória")
    private String categoria;

    @Schema(description = "Disponibilidade do produto", example = "true", required = true)
    @NotNull(message = "A disponibilidade do produto é obrigatória")
    private Boolean disponivel;

    @Schema(description = "ID do restaurante", example = "1", required = true)
    @NotNull(message = "O restaurante é obrigatório")
    private Long restauranteId;

}