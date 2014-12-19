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

    public boolean validarTipoMarcacao(String tipoMarcacao)
    {
        if(tipoMarcacao != null && tipoMarcacao.length() > 4)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean validarLocalidade(String tipoMarcacao)
    {
        if(tipoMarcacao != null && tipoMarcacao.length() > 4)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean validarLongitude(double longitude)
    {
        if(longitude >= -180 && longitude <= 180 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean validarLatitude(double latitude)
    {
        if(latitude >= -90 && latitude <= 90)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean validarTempo24H(String hora)
    {
        String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
        Matcher matcher = pattern.matcher(hora);
        boolean resultado = matcher.matches();
        return resultado;
    }

    public boolean validarDataAMD(final String date){

        String DATE_VALIDATION_PATTERN = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)";
        String DATA_VALIDATION = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(DATA_VALIDATION);
        matcher = pattern.matcher(date);

        if(matcher.matches()){
            matcher.reset();
            if(matcher.find()){
                String dd = matcher.group(3);
                String mm = matcher.group(2);
                int yy = Integer.parseInt(matcher.group(1));
                if (dd.equals("31") &&  (mm.equals("4") || mm .equals("6") || mm.equals("9") ||
                        mm.equals("11") || mm.equals("04") || mm .equals("06") ||
                        mm.equals("09"))) {
                    return false;
                } else if (mm.equals("2") || mm.equals("02")) {

                    if(yy % 4==0){
                        if(dd.equals("30") || dd.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        if(dd.equals("29")||dd.equals("30")||dd.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
