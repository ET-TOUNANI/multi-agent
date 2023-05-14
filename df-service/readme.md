# Secure Communication between Agents

## Description

Secure communication between agents using JADE (Java Agent Development Framework) 4.5.0
RSA encryption algorithm is used to encrypt messages between agents.
AES encryption algorithm is used to encrypt messages between agents.

## Technologies Used

- Java 8
- JADE (Java Agent Development Framework) 4.5.0

## Table of Contents

- Secure communication using RSA
- Secure communication using AES

## Secure communication using RSA

### Description

RSA is an asymmetric encryption algorithm. It is based on the difficulty of factoring the product of two large prime numbers. The public key consists of two numbers where one number is multiplication of two large prime numbers. And private key is also derived from the same two prime numbers. So if somebody can factor the large number, the private key is compromised. Therefore encryption strength totally lies on the key size and if we double or triple the key size, the strength of encryption increases exponentially. RSA keys can be typically 1024 or 2048 bits long, but experts believe that 1024 bit keys could be broken in the near future. But till now it seems to be an infeasible task.

### Code

> AgentClient

```Java
public class AgentClient extends Agent {
    @Override
    protected void setup() {
        String msg="hi fro enset";
        String encodePbkey=(String) getArguments()[0];
        byte[] decodePbkey=Base64.getDecoder().decode(encodePbkey);

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                try {
                    KeyFactory keyFactory=KeyFactory.getInstance("RSA");
                    PublicKey publicKey=keyFactory.generatePublic(new X509EncodedKeySpec(decodePbkey));
                    Cipher cipher=Cipher.getInstance("RSA");
                    cipher.init(Cipher.ENCRYPT_MODE,publicKey);
                    byte[] encryptMsg=cipher.doFinal(msg.getBytes());
                    String encryptEncodMsg= Base64.getEncoder().encodeToString(encryptMsg);
                    ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
                    aclMessage.setContent(encryptEncodMsg);
                    aclMessage.addReceiver(new AID("server",AID.ISLOCALNAME));
                    send(aclMessage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }
    //....

}
```

> AgentServer

```Java
public class AgentServer extends Agent {

    @Override
    protected void setup() {
        String encodePrk= (String) getArguments()[0];
        byte[] decoderPrk =Base64.getDecoder().decode(encodePrk);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message=receive();
                if(message!=null){
                    String encryptEncodMsg=message.getContent();
                    byte[] encruptMsg= Base64.getDecoder().decode(encryptEncodMsg);
                    try {
                        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
                       PrivateKey privateKey= keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoderPrk));

                        Cipher cipher=Cipher.getInstance("RSA");
                        cipher.init(Cipher.DECRYPT_MODE,privateKey);
                        byte[] decryptMessage=cipher.doFinal(encruptMsg);
                        System.out.println(new String(decryptMessage));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else block();
            }
        });
    }

   // ....
}
```

> Generate RSA keys

```Java
public class GenerateRsaKeys {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize( 512);
        KeyPair keyPair=keyPairGenerator.generateKeyPair();
        PrivateKey privateKey=keyPair.getPrivate();
        PublicKey publicKey=keyPair.getPublic();
        String encodePRK= Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String encodePbK= Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("**********private*********");
        System.out.println(encodePRK);
        System.out.println("*********public**********");
        System.out.println(encodePbK);
        System.out.println("*******************");
    }
}

```

> Run

```Java
public class Main {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("gui","true");
       AgentContainer mainContainer = runtime.createMainContainer(profile);
       mainContainer.start();

    }
}
//*************************
public class SaimpleContainer1 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container =runtime.createAgentContainer(profile);
        String pbKey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIdwRUDZtqd7XA0/Mje3JJxMO53r9a/HHk1ZeqLQzEsIqoSIffU7KCTGEDilz26TTjsTJ8KkcyXv4F6/DDbf4bsCAwEAAQ==";
        AgentController agentClient=container.createNewAgent("client","org.example.rsa.agents.AgentClient",new  Object[]{pbKey});
        agentClient.start();
    }

}
//µµµµµµµµµµµµµµµµµµµµµµµµµ
public class SimpleContainer2 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container =runtime.createAgentContainer(profile);
        String prKey="MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAh3BFQNm2p3tcDT8yN7cknEw7nev1r8ceTVl6otDMSwiqhIh99TsoJMYQOKXPbpNOOxMnwqRzJe/gXr8MNt/huwIDAQABAkAqwgT7DkCd61IKVJM0B7ulJGQF3UJ5NfGZFdCjqit03FSw4eEYySo/iyfBuZr0nnZq3UY6jSBg+fGtvcwXvesxAiEA66CTrL5f9QjuhvS039APSXVXmZC2hRE2Oyc5jh62p28CIQCTJhiTaibpDyeMq2NtstbuNk6iN4exFIXxPcOVPOJkdQIhALzNOSckkD0GgCJROBFSZL1msd+Rzj5HsV2qq3qOb6svAiBJMZ7x3NCEBzS+BRaNH3NHxJ2ylPqa+8FwcS+TUVivbQIhAKzEL0xD8+yPtDskVgcAJ0zx63MhYJusV8c8WkDjKwSZ";
        AgentController agentClient=container.createNewAgent("server","org.example.rsa.agents.AgentServer",new  Object[]{prKey});
        agentClient.start();
    }
}


```

## Secure communication between two agents using AES

### Description

AES is a symmetric encryption algorithm. It is a block cipher algorithm that uses the same key for encryption and decryption. The key length is 128 bits, 192 bits, or 256 bits. The default key length is 128 bits. The plaintext is divided into blocks of 128 bits, and then the encryption is performed. The encryption process is divided into 10 rounds, and the last round is different from the previous 9 rounds. The decryption process is the reverse of the encryption process. The decryption process is divided into 10 rounds, and the first round is different from the previous 9 rounds.

### Code

> AgentClient

```Java
public class AgentClient extends Agent {
    @Override
    protected void setup() {
        String msg="hi fro enset";
        String key=(String) getArguments()[0];
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                SecretKey secretKey=new SecretKeySpec(key.getBytes(),"AES");
                try {
                    Cipher cipher=Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE,secretKey);
                    byte[] encryptMsg=cipher.doFinal(msg.getBytes());
                    String encryptEncodMsg= Base64.getEncoder().encodeToString(encryptMsg);
                    ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
                    aclMessage.setContent(encryptEncodMsg);
                    aclMessage.addReceiver(new AID("server",AID.ISLOCALNAME));
                    send(aclMessage);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }
    //....

}
```

> AgentServer

```Java
public class AgentServer extends Agent {

    @Override
    protected void setup() {
        String password= (String) getArguments()[0];
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message=receive();
                if(message!=null){
                    String encryptEncodMsg=message.getContent();
                    byte[] encruptMsg= Base64.getDecoder().decode(encryptEncodMsg);
                    SecretKey secretKey=new SecretKeySpec(password.getBytes(),"AES");
                    try {
                        Cipher cipher=Cipher.getInstance("AES");
                        cipher.init(Cipher.DECRYPT_MODE,secretKey);
                        byte[] decryptMessage=cipher.doFinal(encruptMsg);
                        System.out.println(new String(decryptMessage));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else block();
            }
        });
    }

   // ....
}
```

> Run

```Java
public class Main {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("gui","true");
       AgentContainer mainContainer = runtime.createMainContainer(profile);
       mainContainer.start();

    }
}
//*************************
public class SaimpleContainer1 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container =runtime.createAgentContainer(profile);
        String password="1234567812345678";
        AgentController agentClient=container.createNewAgent("client","org.example.aes.agents.AgentClient",new  Object[]{password});
        agentClient.start();
    }

}
//µµµµµµµµµµµµµµµµµµµµµµµµµ
public class SimpleContainer2 {
    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container =runtime.createAgentContainer(profile);
        String password="1234567812345678";
        AgentController agentClient=container.createNewAgent("server","org.example.aes.agents.AgentServer",new  Object[]{password});
        agentClient.start();
    }
}


```

## Demo

<center><img src="demo/1.png"></center>
<center><img src="demo/2.png"></center>
<center><img src="demo/3.png"></center>
## WHO

This project was created by [Abderrahmane Ettounani].
