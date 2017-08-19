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
        BigInt a = new BigInt("10000000000000000");
        BigInt b = new BigInt("1");
        BigInt expectedResult = new BigInt("9999999999999999");

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
}