package br.com.fernando;

import br.com.fernando.enums.Align;

import static br.com.fernando.enums.Align.LEFT;
import static br.com.fernando.enums.Align.RIGHT;
import static java.lang.String.format;
import static java.util.Objects.isNull;

public class ImpressorCupom {

    private final int MAX_LIMIT = 50;
    private final String BREAK_LINE = "\n";
    private final String PLACEHOLDER = "%";
    private final String DEFAULT_CONVERSION = "s";
    private final String STRING_EMPTY = "\s";
    private StringBuilder conteudo = new StringBuilder();


    public void imprimir(Cupom cupom){
        /**
         vamos somente impimir o resultado no console
         mas este mesmo conteúdo poderá proporicionar várias saidas
         logo a variável conteudo deveria ser retornada para outros "processadores"
         como gerador de arquivo txt, pdf ou até mesmo envio por e-mail
         */
        generateTextCupom(cupom);
        System.out.println(conteudo.toString());
        //em caso de resolver explorar algumas formas de apresentação
        //return conteudo.toString();
    }


    private void generateTextCupom(Cupom cupom) {
        appendDashed();
        appendLine(LEFT, cupom.nomeFantasia);
        Endereco end = cupom.endereco;
        appendLine("%s N. %s %s %s", LEFT, end.logradouro, end.numero, end.complemento, end.bairro);
        appendLine("%s-%s", LEFT, end.cidade, end.uf);
        appendLine("CPF/CNPJ:%s%20s%s", LEFT, cupom.cpf, STRING_EMPTY, cupom.data);
        appendLine("IE:%s%27s%tH:%3$tM:%3$tS", LEFT, cupom.ie, STRING_EMPTY, cupom.hora);
        appendLine("IM:%s%33s%d", LEFT, cupom.im, STRING_EMPTY, cupom.ccf);
        appendLine(RIGHT, "CDD:" + cupom.cdd);
        appendDashed();
        appendLine(LEFT, "CUPOM FISCAL");
        appendLine("ITEM COD. %-30s%10s", LEFT, "DESCRIÇÃO","VALOR");
        for (int i = 0; i < cupom.itens.size(); i++) {
            CupomItem item = cupom.itens.get(i);
            appendLine("%03d\s\s%d\s\s%-35s%,5.2f", LEFT, i + 1, item.sku, item.descricao, item.preco);
        }
        appendDashed();
    }

    private void appendLine(Align align, Object... atributes) {
        String format = PLACEHOLDER + DEFAULT_CONVERSION;
        appendLine(format, align, atributes);
    }

    private void appendLine(String format, Align align, Object... atributes) {
        conteudo.append(
            format(
                PLACEHOLDER +
                (isNull(align) ? STRING_EMPTY : align.getSymbol()) +
                MAX_LIMIT +
                DEFAULT_CONVERSION +
                BREAK_LINE,
                format(format, atributes)
            )
        );
    }

    private void appendDashed() {
        conteudo.append(new String(new char[MAX_LIMIT]).replace("\0", "-") + BREAK_LINE);
    }

    private String cpfCnpj(String cpfCnpj){
        String newCnpj = "";
        if(cpfCnpj.length()==11)
            newCnpj = cpfCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        else
            newCnpj = cpfCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        return newCnpj;
    }
}

