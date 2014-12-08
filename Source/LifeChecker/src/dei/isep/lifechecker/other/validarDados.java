package dei.isep.lifechecker.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validarDados {

	public validarDados() {
	};

	public boolean mailValidacaoFormato(String mail) {
		boolean resultado = false;
		Pattern patternMail = Pattern.compile(
				"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matchPatternMail = patternMail.matcher(mail);
		resultado = matchPatternMail.find();
		return resultado;
	}

	public boolean passValidarFormato(String password) {
		boolean resultado = false;

		Pattern patternPassword = Pattern
				.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,30})");
		Matcher matchPatternPassword = patternPassword.matcher(password);
		resultado = matchPatternPassword.find();

		return resultado;
	}

	public boolean nomeApelidoVerificar(String conteudo) {
		if (conteudo != null) {
			if (conteudo.length() > 1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public boolean contactoVerificar(String conteudo) {
		if (conteudo != null && conteudo.length() > 8) {
			return true;
		} else {
			return false;
		}
	}

	public boolean periodicidadeVerificar(String conteudo) {
		if (conteudo.length() != 0) {
			return true;
		} else {
			return false;
		}
	}

}
