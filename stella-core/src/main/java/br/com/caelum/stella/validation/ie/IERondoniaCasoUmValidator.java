package br.com.caelum.stella.validation.ie;

import java.util.regex.Pattern;

import br.com.caelum.stella.MessageProducer;
import br.com.caelum.stella.SimpleMessageProducer;
import br.com.caelum.stella.validation.DigitoVerificadorInfo;
import br.com.caelum.stella.validation.RotinaDeDigitoVerificador;
import br.com.caelum.stella.validation.ValidadorDeDV;

class IERondoniaCasoUmValidator extends AbstractIEValidator {

    private static final int MOD = 11;

    // TAMANHO = 9;
    private static final String MISSING_LEFT_SIDE_ZEROS = "00000";

    private static final int DVX_POSITION = MISSING_LEFT_SIDE_ZEROS.length() + 9;

    private static final Integer[] DVX_MULTIPLIERS = IEConstraints.P4;

    private static final RotinaDeDigitoVerificador[] rotinas = { IEConstraints.Rotina.E, IEConstraints.Rotina.POS_IE };

    private static final DigitoVerificadorInfo DVX_INFO = new DigitoVerificadorInfo(1, rotinas, MOD, DVX_MULTIPLIERS,
            DVX_POSITION);

    private static final ValidadorDeDV DVX_CHECKER = new ValidadorDeDV(DVX_INFO);

    public static final Pattern FORMATED = Pattern.compile("([1-9]\\d{2})[.](\\d{5})[-](\\d{1})");

    public static final Pattern UNFORMATED = Pattern.compile("([1-9]\\d{2})(\\d{5})(\\d{1})");
	
    /**
	 * Este considera, por padrão, que as cadeias estão formatadas e utiliza um
	 * {@linkplain SimpleMessageProducer} para geração de mensagens.
	 */
	public IERondoniaCasoUmValidator() {
		super(true);
	}

	/**
	 * O validador utiliza um {@linkplain SimpleMessageProducer} para geração de
	 * mensagens.
	 * 
	 * @param isFormatted
	 *            considerar cadeia formatada quando <code>true</code>
	 */
	public IERondoniaCasoUmValidator(boolean isFormatted) {
		super(isFormatted);
	}

	public IERondoniaCasoUmValidator(MessageProducer messageProducer, boolean isFormatted) {
		super(messageProducer, isFormatted);
	}


	@Override
	protected Pattern getUnformattedPattern() {
		return UNFORMATED;
	}

	@Override
	protected Pattern getFormattedPattern() {
		return FORMATED;
	}

    protected boolean hasValidCheckDigits(String value) {
        String testedValue = MISSING_LEFT_SIDE_ZEROS + value;
        return DVX_CHECKER.isDVValid(testedValue);
    }
}
