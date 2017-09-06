package bigint;

import org.junit.Ignore;
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
    public void shouldSubtractWithOverflow() throws Exception {
        BigInt a = new BigInt("1");
        BigInt b = new BigInt("2");
        BigInt expectedResult = new BigInt("-1");

        assertThat(a.subtract(b)).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSubtractLarge2() throws Exception {
        BigInt a = new BigInt("609258644980293364");
        BigInt b = new BigInt("11907858797062763600287");
        BigInt expectedResult = new BigInt("-11907249538417783306923");

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

    @Test
    public void shouldCompare() throws Exception {
        BigInt a = new BigInt("-16");
        BigInt b = new BigInt("-17");

        assertThat(a.compare(b)).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotDivideByZero() throws Exception {
        BigInt a = new BigInt("100");
        BigInt b = new BigInt("0");

        a.divide(b);
    }

    @Test
    public void shouldDivideGreaterBySPart() throws Exception {
        BigInt a = new BigInt("100");
        BigInt b = new BigInt("100000000");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(new BigInt("0"));
        assertThat(div.getRemainder()).isEqualTo(a);
    }

    @Test
    public void shouldDivideGreaterWithEqualSPart() throws Exception {
        BigInt a = new BigInt("10000000000");
        BigInt b = new BigInt("10100000000");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(new BigInt("0"));
        assertThat(div.getRemainder()).isEqualTo(a);
    }

    @Test
    public void shouldDivideSimple() throws Exception {
        BigInt a = new BigInt("10");
        BigInt b = new BigInt("2");
        BigInt expectedResult = new BigInt("5");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
    }

    @Test
    public void shouldDivide() throws Exception {
        BigInt a = new BigInt("100000000");
        BigInt b = new BigInt("2");
        BigInt expectedResult = new BigInt("50000000");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
    }

    @Test
    public void shouldDivide2() throws Exception {
        BigInt a = new BigInt("17");
        BigInt b = new BigInt("-8");
        BigIntDiv expectedResult = new BigIntDiv(new BigInt("-2"), new BigInt("1"));

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult.getDivResult());
        assertThat(div.getRemainder()).isEqualTo(expectedResult.getRemainder());
    }

    @Test
    public void shouldDivide3() throws Exception {
        BigInt a = new BigInt("-17");
        BigInt b = new BigInt("-8");
        BigIntDiv expectedResult = new BigIntDiv(new BigInt("3"), new BigInt("7"));

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult.getDivResult());
        assertThat(div.getRemainder()).isEqualTo(expectedResult.getRemainder());
    }

    @Test
    public void shouldDivide4() throws Exception {
        BigInt a = new BigInt("-8");
        BigInt b = new BigInt("-17");
        BigIntDiv expectedResult = new BigIntDiv(new BigInt("1"), new BigInt("9"));

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult.getDivResult());
        assertThat(div.getRemainder()).isEqualTo(expectedResult.getRemainder());
    }


    @Test
    public void shouldDivide5() throws Exception {
        BigInt a = new BigInt("-8");
        BigInt b = new BigInt("17");
        BigIntDiv expectedResult = new BigIntDiv(new BigInt("-1"), new BigInt("9"));

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult.getDivResult());
        assertThat(div.getRemainder()).isEqualTo(expectedResult.getRemainder());
    }

    @Test
    public void shouldDivideLarge() throws Exception {
        BigInt a = new BigInt("225461820030056738863111393842522918646434913785508706785223950926908750687391732247955346997611273062097910735906920001391888212823475570974758772091099074195005291818882084891523756343366089648730273588212737213447787531009284731961359");
        BigInt b = new BigInt("1161773002875530970475777318025858646972144071496504896369373542143925303581117880759012959257904240775262220379722624450259881493669369582103579798349025470539165815324808002121323272794820292319");
        BigInt expectedResult = new BigInt("194067016079742792936305387276325348880296");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
    }

    @Test
    public void shouldDivideLarge2() throws Exception {
        BigInt a = new BigInt("3561599");
        BigInt b = new BigInt("3430399");
        BigInt expectedResult = new BigInt("1");
        BigInt expectedRemainder = new BigInt("131200");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge3() throws Exception {
        BigInt a = new BigInt("11907858797062763600287");
        BigInt b = new BigInt("609258644980293364");
        BigInt expectedResult = new BigInt("19544");
        BigInt expectedRemainder = new BigInt("507839567910094271");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge4() throws Exception {
        BigInt a = new BigInt("609258644980293364");
        BigInt b = new BigInt("11907858797062763600287");
        BigInt expectedResult = new BigInt("0");
        BigInt expectedRemainder = new BigInt("609258644980293364");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge5() throws Exception {
        BigInt a = new BigInt("275037643193238187969754635193967098168234");
        BigInt b = new BigInt("2789096463851958435408110961618870154822");
        BigInt expectedResult = new BigInt("98");
        BigInt expectedRemainder = new BigInt("1706189735746261299759760955317822995678");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge6() throws Exception {
        BigInt a = new BigInt("6081438698999606240186790021334933066970638428563610408662925699467423912567624");
        BigInt b = new BigInt("78047574151725182769714241091983435073053370965");
        BigInt expectedResult = new BigInt("77919637670957395462552984727876");
        BigInt expectedRemainder = new BigInt("39977248264123496066904338260006113918908047284");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge7() throws Exception {
        BigInt a = new BigInt("295147905179352825855");
        BigInt b = new BigInt("-9650076807698");
        BigInt expectedResult = new BigInt("-30585031");
        BigInt expectedRemainder = new BigInt("6863528457217");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }


    @Test
    public void shouldDivideLarge8() throws Exception {
        BigInt a = new BigInt("-1208925819614629174706175");
        BigInt b = new BigInt("-281474976710655");
        BigInt expectedResult = new BigInt("4294967297");
        BigInt expectedRemainder = new BigInt("281470681743360");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge9() throws Exception {
        BigInt a = new BigInt("-554751");
        BigInt b = new BigInt("-3456");
        BigInt expectedResult = new BigInt("161");
        BigInt expectedRemainder = new BigInt("1665");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge10() throws Exception {
        BigInt a = new BigInt("-9650076807698");
        BigInt b = new BigInt("295147905179352825855");
        BigInt expectedResult = new BigInt("-1");
        BigInt expectedRemainder = new BigInt("295147895529276018157");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge11() throws Exception {
        BigInt a = new BigInt("-1208925819614629174706175");
        BigInt b = new BigInt("17592186044415");
        BigInt expectedResult = new BigInt("-68719476737");
        BigInt expectedRemainder = new BigInt("17523466567680");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldDivideLarge13() throws Exception {
        BigInt a = new BigInt("497323236293994552945025999384123393152510078904252194576614865391888856047008115326975");
        BigInt b = new BigInt("1461501636990620551361974531748726005748293697535");
        BigInt expectedResult = new BigInt("340282366920938463463374607431768211456");
        BigInt expectedRemainder = new BigInt("79228162495817593524129366015");

        BigIntDiv div = a.divide(b);
        assertThat(div.getDivResult()).isEqualTo(expectedResult);
        assertThat(div.getRemainder()).isEqualTo(expectedRemainder);
    }

    @Test
    public void shouldCreateBigIntFromLong() throws Exception {
        BigInt fromString = new BigInt("123");
        BigInt fromLong = new BigInt(123);

        assertThat(fromString).isEqualTo(fromLong);
    }

    @Test
    public void shouldCreateBigIntFromLong2() throws Exception {
        BigInt fromString = new BigInt("-123");
        BigInt fromLong = new BigInt(-123);

        assertThat(fromString).isEqualTo(fromLong);
    }

    @Test
    public void shouldCreateBigIntFromLong3() throws Exception {
        BigInt fromString = new BigInt("123456789123");
        BigInt fromLong = new BigInt(123456789123L);

        assertThat(fromString.getsPart()).isEqualTo(1);
        assertThat(fromLong.getsPart()).isEqualTo(1);
        assertThat(fromString).isEqualTo(fromLong);
    }

    @Test
    public void shouldCreateBigIntFromLong4() throws Exception {
        BigInt fromString = new BigInt("-123456789123");
        BigInt fromLong = new BigInt(-123456789123L);

        assertThat(fromString.getsPart()).isEqualTo(1);
        assertThat(fromLong.getsPart()).isEqualTo(1);
        assertThat(fromString).isEqualTo(fromLong);
    }

    @Test
    public void shouldConvertFromHex() throws Exception {
        BigInt dec = new BigInt(16);
        BigInt hex = BigInt.fromHexString("10");

        assertThat(hex).isEqualTo(dec);
    }

    @Test
    public void shouldConvertFromHex2() throws Exception {
        BigInt dec = new BigInt(65537);
        BigInt hex = BigInt.fromHexString("010001");

        assertThat(hex).isEqualTo(dec);
    }

    @Test
    public void shouldConvertFromHex3() throws Exception {
        BigInt dec = new BigInt(1283248328534L);
        BigInt hex = BigInt.fromHexString("012ac78f8f56");

        assertThat(hex).isEqualTo(dec);
    }

    @Test
    public void shouldConvertFromHex4() throws Exception {
        BigInt dec = new BigInt(15);
        BigInt hex = BigInt.fromHexString("0f");

        assertThat(hex).isEqualTo(dec);
    }

    @Test
    public void shouldConvertFromHex5() throws Exception {
        BigInt dec = new BigInt(325436436457657657L);
        BigInt hex = BigInt.fromHexString("04842eb6f8071539");

        assertThat(hex).isEqualTo(dec);
    }

    @Test
    @Ignore("Correct, but takes very long.")
    public void shouldConvertFromHex6() throws Exception {
        BigInt dec = new BigInt("123494343940300399459434350309932883495060594943283283283218128439395945460600403439229312432543543645775687698798007897874565534132432543645765768987078078087012349434394030039945943435030993288349506059494328328328321812843939594546060040343922931243254354364577568769879800789787456553413243254364576576898707807808701234943439403003994594343503099328834950605949432832832832181284393959454606004034392293");
        BigInt hex = BigInt.fromHexString("01419ccb955906491b66aabc6278b3faf9ffe3f03c8c12492f7f9a5869fcc31c70d2ef1b1d3cc52b9c65895dc0bb5b9530f750f1bf423fe61975aab77298f4735ccb85d083f18ef2ddf4eea8d833bfb28aa64a377fb2ba948b0cb154988f791ea0431156d1e118d533c28c2960244f9c5de5078a2680a1499c3e214b6b6a22faa050a377248f18730ba598e177cd82dd6f9091e3eb06082a7ddf4062f3a07e26bdabcf55a201fe76d0e5");

        assertThat(hex).isEqualTo(dec);
    }

    @Test
    public void shouldConvertFromHex7() throws Exception {
        BigInt dec = new BigInt("4534346436456456456567879800908797687546535432121242124");
        BigInt hex = BigInt.fromHexString("2f573dbb43f0c18b04e060d444cfc97e65285c8a3c520c");

        assertThat(hex).isEqualTo(dec);
    }

    @Test
    public void shouldConvertToHex() throws Exception {
        BigInt dec = new BigInt(16);
        String expectedResult = "10";

        assertThat(dec.toHexString()).isEqualTo(expectedResult);
    }

    @Test
    public void shouldConvertToHex2() throws Exception {
        BigInt dec = new BigInt(65537);
        String expectedResult = "010001";

        assertThat(dec.toHexString()).isEqualTo(expectedResult);
    }

    @Test
    public void shouldConvertToHex3() throws Exception {
        BigInt dec = new BigInt(1283248328534L);
        String expectedResult = "012ac78f8f56";

        assertThat(dec.toHexString()).isEqualTo(expectedResult);
    }

    @Test
    public void shouldConvertToHex4() throws Exception {
        BigInt dec = new BigInt("6456456576585865858865876");
        String expectedResult = "055735365c37d2c2275ad4";

        assertThat(dec.toHexString()).isEqualTo(expectedResult);
    }

    @Test
    public void shouldNegateHexInTwosComplement() throws Exception {
        String a = "0000000000000000000000000000000000000000000000000000000000000001";
        String b = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";

        assertThat(BigInt.negative(BigInt.fromHexString(a)).toHexStringTwosComplement(64)).isEqualTo(b);
    }

    @Test
    public void shouldNegateHexInTwosComplement2() throws Exception {
        String a = "000000000000000000000000000000000000000000000000064afc96999b5e34";
        String b = "fffffffffffffffffffffffffffffffffffffffffffffffff9b503696664a1cc";

        assertThat(BigInt.negative(BigInt.fromHexString(a)).toHexStringTwosComplement(64)).isEqualTo(b);
    }
}
