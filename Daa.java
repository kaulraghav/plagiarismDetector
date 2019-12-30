import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.*;
import java.io.*;

 class KnuthMorrisPratt
{
    
    int pos;
    private int[] failure;
    
    public KnuthMorrisPratt(String text, String pat)
    {
        
        failure = new int[pat.length()];
        fail(pat);
        
         pos = posMatch(text, pat);
        if (pos != -1)
            System.out.println("Pattern found at position : "+ pos);
           
    
    }
    /** Failure function for a pattern **/
    private void fail(String pat)
    {
        int n = pat.length();
        failure[0] = -1;
        for (int j = 1; j < n; j++)
        {
            int i = failure[j - 1];
            while ((pat.charAt(j) != pat.charAt(i + 1)) && i >= 0)
                i = failure[i];
            if (pat.charAt(j) == pat.charAt(i + 1))
                failure[j] = i + 1;
            else
                failure[j] = -1;
        }
    }
    /** Function to find match for a pattern **/
    private int posMatch(String text, String pat)
    {
        int i = 0, j = 0;
        int lens = text.length();
        int lenp = pat.length();
        while (i < lens && j < lenp)
        {
            if (text.charAt(i) == pat.charAt(j))
            {
                i++;
                j++;
            }
            else if (j == 0)
                i++;
            else
                j = failure[j - 1] + 1;
        }
        return ((j == lenp) ? (i - lenp) : -1);
    }

}
 class RabinKarp
{
    /** String Pattern **/
    private String pat;
    /** Pattern hash value **/
    private long patHash;
    /** Pattern length **/
    private int M;
    /** Large prime **/
    private long Q;
    /** Radix **/
    private int R;
    /** R^(M-1) % Q **/
    private long RM;
     int pos;
    
    public RabinKarp(String txt, String pat)
    {
        this.pat = pat;
        R = 256;
        M = pat.length();
        Q = longRandomPrime();
        /** precompute R^(M-1) % Q for use in removing leading digit **/
        RM = 1;
        for (int i = 1; i <= M-1; i++)
           RM = (R * RM) % Q;
        patHash = hash(pat, M);
        pos = search(txt);
        if (pos != -1)
            System.out.println("Pattern found at position : "+ pos);
           
    }
    /** Compute hash **/
    private long hash(String key, int M)
    {
        long h = 0;
        for (int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }
    /** Function check **/
    private boolean check(String txt, int i)
    {
        for (int j = 0; j < M; j++)
            if (pat.charAt(j) != txt.charAt(i + j))
                return false;
        return true;
    }
    /** Function to check for exact match**/
    private int search(String txt)
    {
        int N = txt.length();
        if (N < M) return N;
        long txtHash = hash(txt, M);
        /** check for match at start **/
        if ((patHash == txtHash) && check(txt, 0))
            return 0;
        /** check for hash match. if hash match then check for exact match**/
        for (int i = M; i < N; i++)
        {
            // Remove leading digit, add trailing digit, check for match.
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            // match
            int offset = i - M + 1;
            if ((patHash == txtHash) && check(txt, offset))
                return offset;
        }
        /** No match **/
        return -1;
    }
    /** Generate a random 31 bit prime **/
    private static long longRandomPrime()
    {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }
   
}

 class LongestCommonSubsequence
{
    
    public String lcs(String str1, String str2)
    {
        int l1 = str1.length();
        int l2 = str2.length();

        int[][] arr = new int[l1 + 1][l2 + 1];

        for (int i = l1 - 1; i >= 0; i--)
        {
            for (int j = l2 - 1; j >= 0; j--)
            {
                if (str1.charAt(i) == str2.charAt(j))
                    arr[i][j] = arr[i + 1][j + 1] + 1;
                else
                    arr[i][j] = Math.max(arr[i + 1][j], arr[i][j + 1]);
            }
        }

        int i = 0, j = 0;
        StringBuffer sb = new StringBuffer();
        while (i < l1 && j < l2)
        {
            if (str1.charAt(i) == str2.charAt(j))
            {
                sb.append(str1.charAt(i));
                i++;
                j++;
            }
            else if (arr[i + 1][j] >= arr[i][j + 1])
                i++;
            else
                j++;
        }
        return sb.toString();
    }

}
class Run
{
	public static String readStringFromFile(String filename)
        {
       
     String builder = "";
     try
     {
         Scanner fileReader = new Scanner(new File(filename));
         while (fileReader.hasNextLine())
         {
      builder = builder + fileReader.nextLine() + "\n";
         }

         return builder;
     }
     catch (Exception e)
     {
         System.out.println(e);
         return null;
     }
        }
    public static void main(String[] args) throws IOException
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File folder = new File("F:\\DAA Project\\Data");
        double lcssSimRatio;
        File[] listOfFiles = folder.listFiles();
        System.out.println("1 : KMP");
        System.out.println("2 : LCSS");
        System.out.println("3 : Rabin-Karp");
        	                
        int ch=Integer.parseInt(br.readLine());
      switch(ch)
      {   
        case 1: for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
   
               String text=readStringFromFile("Data/"+ listOfFiles[i].getName());
               String pattern=readStringFromFile("input.txt");
                        
                         KnuthMorrisPratt kmp = new KnuthMorrisPratt(text,pattern);   
                           if(kmp.pos!=-1)
                	       System.out.println("Plagiarized from "+listOfFiles[i].getName());
                	      

                      }
                  }
                  break;
         case 2: for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
   
               String text=readStringFromFile("Data/"+ listOfFiles[i].getName());
               String pattern=readStringFromFile("input.txt");
               LongestCommonSubsequence obj = new LongestCommonSubsequence(); 
                 String result = obj.lcs(text, pattern);
                if(result.length()>=pattern.length())
                 { 
                 System.out.println("Plagiarized from "+listOfFiles[i].getName());
                 }
                }
              }
               break;
         case 3: for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
   
               String text=readStringFromFile("Data/"+ listOfFiles[i].getName());
               String pattern=readStringFromFile("input.txt");
               RabinKarp rk = new RabinKarp(text, pattern);
                if(rk.pos!=-1)
                	System.out.println("Plagiarized from "+listOfFiles[i].getName());
                }
              }
               break;    
         default: 
                   System.out.println("Invalid Choice!");

       }
   }
}
        
