package web.funcionalidade;

import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import commons.BaseTest;
import commons.funcionalidade.VariaveisEstaticas;
import io.cucumber.datatable.DataTable;
import web.bean.enums.AgendamentoCPT85Enum;
import web.bean.interfaces.AgendamentoCPT85Interface;
import web.pages.AgendamentoCPT85Page;

public class AgendamentoFuncionalidade extends BaseTest {

	private AgendamentoCPT85Page agenda;

	public AgendamentoFuncionalidade() {
		this.agenda = new AgendamentoCPT85Page(webDriver);
	}

	public void preencherCampos(DataTable params) {
		params.asMaps().forEach(line -> {
			Set<String> headers = line.keySet();
			headers.forEach(header -> {
				this.preencherCampoValor(header, line.get(header));
			});
		});
	}

	public void alterarInformacoesAgenda() {
		this.preencherCampoValor("Hora Prev Entrada", "1700");
		this.preencherCampoValor("Transportadora", "NomeAlterado");
		this.preencherCampoValor("Contato", "Automação");
		this.preencherCampoValor("Fone", "11 54859632");
		this.preencherCampoValor("Perecivel", "S");
		this.preencherCampoValor("Alto Risco", "N");
	}

	public void preencherCampoValor(String campo, String valor) {
		AgendamentoCPT85Interface intAgend = AgendamentoCPT85Enum.valueOf(campo.replace(" ", "_").toUpperCase());
		intAgend.getElement(this.agenda).clear();
		intAgend.getElement(this.agenda).sendKeys(valor);
		addEvidenciaWeb("Preechimeno do campo: " + campo + " com o valor: " + valor);
	}

	public void limparCampos(String campo) {
		AgendamentoCPT85Interface intAgend = AgendamentoCPT85Enum.valueOf(campo.replace(" ", "_").toUpperCase());
		intAgend.getElement(this.agenda).clear();
		addEvidenciaWeb("Campo: " + campo + " está vazio");
	}

	public String retornaValorCampo(String campo) {
		AgendamentoCPT85Interface intAgend = AgendamentoCPT85Enum.valueOf(campo.replace(" ", "_").toUpperCase());
		addEvidenciaWeb("Retornando o valor do campo" + campo);
		return intAgend.getElement(this.agenda).getAttribute("value");

	}

	public boolean validaCampoVazio(String campo) {
		AgendamentoCPT85Interface intAgend = AgendamentoCPT85Enum.valueOf(campo.replace(" ", "_").toUpperCase());
		addEvidenciaWeb("Campo " + campo);
		return intAgend.getElement(this.agenda).getAttribute("value").isEmpty();

	}

	public void clicarBotaoIncluir() {
		addEvidenciaWeb("Clicar no Botão Incluir");
		this.agenda.getButtonIncluir().click();
	}

	public void clicarBotaoConsultar() {
		addEvidenciaWeb("Clicar no Botão Consultar");
		this.agenda.getButtonConsultar().click();
		addEvidenciaWeb("Consulta realizada, dados retornados");
	}

	public void incluirAguardar() {
		this.clicarBotaoIncluir();
		wait.until(
				ExpectedConditions.not(ExpectedConditions.attributeToBe(this.agenda.getInputAgenda(), "value", "0")));
		VariaveisEstaticas.setNUMERO_AGENDA(this.agenda.getInputAgenda().getAttribute("value"));
		addEvidenciaWeb("Criação da agenda");
	}

	public void clicarBotaoAlterar() {
		addEvidenciaWeb("Clicar no Botão Alterar");
		this.agenda.getButtonAlterar().click();
	}

	public String retornaMensagemExibida() {
		wait.until(ExpectedConditions.visibilityOf(this.agenda.getMsgExibida()));
		addEvidenciaWeb("Mensagem Exibida");
		return this.agenda.getMsgExibida().getText();
	}

	public void deletarConsultar() {
		this.agenda.getButtonDeletar().click();
		addEvidenciaWeb("Botão deletar acionado");
		this.agenda.getButtonConsultar().click();
		addEvidenciaWeb("Consulta do registro recem deletado");
	}

	public String retornaMensagemAlert() {
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = webDriver.switchTo().alert();
		String texto = alert.getText().replaceAll("\\r\\n|\\n","");
		alert.accept();
		addEvidenciaWeb("Mensagens exibidas no Alert  " + texto);
        return texto;
	}
	
	public void consultarPedido() {
		this.agenda.getButtonConsultarPedido().click();
		addEvidenciaWeb("Consultando pedido na agenda");
	}
	
	public void IncluirPedido() {
		this.agenda.getIncluirPedido().click();
		addEvidenciaWeb("Botão incluir pedido ao agendamento acionado");
	}
	
	public void finalizarAgendamento() {
		this.agenda.getButtonFinalizar().click();
		addEvidenciaWeb("Agendamento Finalizado");
	}
	
	public void reenviarSNF() {
		this.agenda.getReenvioSNF().click();
	}
}
