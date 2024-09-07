package onl.tran.service.util;

import lombok.RequiredArgsConstructor;
import onl.tran.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerateWalletNumber {

 private final WalletRepository walletRepository;

 /**
  * GENERATE RANDOM NUMBER FOR WALLER NUMBER
  */
 private String generateRandom16DigitNumber() {
  Random random = new Random();
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < 16; i++) {
   sb.append(random.nextInt(10));
  }
  return sb.toString();
 }


 /**
  * CHECK GENERATED NUMBER IS NOT EXIST IN ANOTHER WALLET AND RETURN
  */
 public String getUnique16DigitNumber() {
  String randomNumber;

  do {
   randomNumber = generateRandom16DigitNumber();
  } while (walletRepository.existsByWalletNumber(randomNumber));

  return randomNumber;
 }

 /**
  *  GENERATE RANDOM NUMBER FOR SMS
  */
 public int generateSixDigitNumber() {
  Random random = new Random();
  return 100000 + random.nextInt(900000);
 }
}
