package bigint;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BigIntTest {

    @Test
    public void shouldCreateFromString() throws Exception {
        BigInt bigInt = new BigInt("0");
        assertThat(bigInt.toString()).isEqualTo("0");
    }

    @Test
    public void shouldCreateFromStringTwoCells() throws Exception {
        String value = "24626230387343119195404049221020";
        BigInt bigInt = new BigInt(value);
        assertThat(bigInt.toString()).isEqualTo(value);
    }

    @Test
    public void shouldCreateFromStringTwoCellsAndRemainder() throws Exception {
        String value = "124626230387343119195404049221020";
        BigInt bigInt = new BigInt(value);
        assertThat(bigInt.toString()).isEqualTo(value);
    }

    @Test
    public void shouldCreateFromStringTwoCellsWithZeros() throws Exception {
        String value = "124626230387343100095404049221020";
        BigInt bigInt = new BigInt(value);
        assertThat(bigInt.toString()).isEqualTo(value);
    }

    @Test
    public void shouldCreateFromStringNegative() throws Exception {
        BigInt bigInt = new BigInt("-1");
        assertThat(bigInt.toString()).isEqualTo("-1");
    }

    @Test
    public void shouldAdd() throws Exception {
        BigInt a = new BigInt("1");
        BigInt b = new BigInt("1");

        assertThat(a.add(b).toString()).isEqualTo("2");
    }

    @Test
    public void shouldAddDifferentLengths() throws Exception {
        BigInt a = new BigInt("124626230387343100095404049221020");
        BigInt b = new BigInt("1");

        assertThat(a.add(b).toString()).isEqualTo("124626230387343100095404049221021");
    }

    @Test
    public void shouldAddDifferentLengths2() throws Exception {
        BigInt a = new BigInt("1");
        BigInt b = new BigInt("124626230387343100095404049221020");

        assertThat(a.add(b).toString()).isEqualTo("124626230387343100095404049221021");
    }

    @Test
    public void shouldAddWithCarryOver() throws Exception {
        BigInt a = new BigInt("1");
        BigInt b = new BigInt("9999999999999999");

        BigInt result = a.add(b);
        assertThat(result.toString()).isEqualTo("10000000000000000");
        assertThat(result.getsPart()).isEqualTo(1);
    }

    @Test
    public void shouldAddLarge() throws Exception {
        BigInt a = new BigInt("2462623038734311919540404922102060100812535267156559306141284193804482469591183623662107405317220323642533138661375");
        BigInt b = new BigInt("1532494079365645322701110259276297880386660201189605375");

        BigInt expectedResult = new BigInt("2462623038734311919540404922102060100812535267156559306141285726298561835236506324772366681615100710302734328266750");

        assertThat(a.add(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSubtract() throws Exception {
        BigInt a = new BigInt("3");
        BigInt b = new BigInt("2");
        BigInt expectedResult = new BigInt("1");

        assertThat(a.subtract(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSubtractWithCarryOver() throws Exception {
        BigInt a = new BigInt("100000000");
        BigInt b = new BigInt("1");
        BigInt expectedResult = new BigInt("99999999");

        assertThat(a.subtract(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSubtractNegative() throws Exception {
        BigInt a = new BigInt("1");
        BigInt b = new BigInt("-1");
        BigInt expectedResult = new BigInt("2");

        assertThat(a.subtract(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSubtractFromNegative() throws Exception {
        BigInt a = new BigInt("-1");
        BigInt b = new BigInt("1");
        BigInt expectedResult = new BigInt("-2");

        assertThat(a.subtract(b)).isEqualTo(expectedResult);
        assertThat(a.subtract(b).getSign()).isNegative();
    }

    @Test
    public void shouldSubtractNegativeFromNegative() throws Exception {
        BigInt a = new BigInt("-1");
        BigInt b = new BigInt("-2");
        BigInt expectedResult = new BigInt("1");

        assertThat(a.subtract(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSubtractLarge() throws Exception {
        BigInt a = new BigInt("2462623038734311919540404922102060100812535267156559306141284193804482469591183623662107405317220323642533138661375");
        BigInt b = new BigInt("1532494079365645322701110259276297880386660201189605375");
        BigInt expectedResult = new BigInt("2462623038734311919540404922102060100812535267156559306141282661310403103945860922551848129019339936982331949056000");

        assertThat(a.subtract(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldMultiply() throws Exception {
        BigInt a = new BigInt("2");
        BigInt expectedResult = new BigInt("4");

        assertThat(a.multiply(a)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldMultiplyLarge() throws Exception {
        BigInt a = new BigInt("78047574151725182769714241091983435073053370965");
        BigInt b = new BigInt("6081438698999606240186790021334933066970638428563610408662925699467423912567624");
        BigInt expectedResult = new BigInt("474641537809342892110368715681177195496687683521433410575809741060619909390481279229924886065386351177919547326154361720637160");

        assertThat(a.multiply(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldMultiplyLarge2() throws Exception {
        BigInt a = new BigInt("6081438698999606240186790021334933066970638428563610408662925699467423912567624");
        BigInt b = new BigInt("78047574151725182769714241091983435073053370965");
        BigInt expectedResult = new BigInt("474641537809342892110368715681177195496687683521433410575809741060619909390481279229924886065386351177919547326154361720637160");

        assertThat(a.multiply(b)).isEqualTo(expectedResult);
    }


    @Test
    public void shouldMultiplyLarge3() throws Exception {
        BigInt a = new BigInt("14912223718264696338397856927216645003797842400010184754757240303931468158450472964700379410986934684302727999568876470644700283834353922266629081467130884678");
        BigInt b = new BigInt("6291236858798355625002240310849051763302244841609637222019848440367474974319646025221986149943002309351421662310425123115121755038535");
        BigInt expectedResult = new BigInt("93816331502993923091074681745972328389387234745632754071327312547773537319847305813663606090731352833926730120370563093374774457937232800827021163412895528374627877544826676795156373404654734474790459480180794112942934255829804307230786697468216690732505036908966356345599307169858531066730");

        assertThat(a.multiply(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldMultiplyNegative() throws Exception {
        BigInt a = new BigInt("1");
        BigInt b = new BigInt("-1");

        assertThat(a.multiply(b)).isEqualTo(b);
    }

    @Test
    public void shouldMultiplyNegative2() throws Exception {
        BigInt a = new BigInt("-1");
        BigInt b = new BigInt("1");

        assertThat(a.multiply(a)).isEqualTo(b);
    }

    @Test
    public void shouldMultiplyNegative3() throws Exception {
        BigInt a = new BigInt("-6291236858798355625002240310849051763302244841609637222019848440367474974319646025221986149943002309351421662310425123115121755038535");
        BigInt b = new BigInt("14912223718264696338397856927216645003797842400010184754757240303931468158450472964700379410986934684302727999568876470644700283834353922266629081467130884678");
        BigInt expectedResult = new BigInt("-93816331502993923091074681745972328389387234745632754071327312547773537319847305813663606090731352833926730120370563093374774457937232800827021163412895528374627877544826676795156373404654734474790459480180794112942934255829804307230786697468216690732505036908966356345599307169858531066730");

        assertThat(a.multiply(b)).isEqualTo(expectedResult);
    }
}