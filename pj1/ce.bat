javac -cp jai_core.jar;jai_codec.jar *.java
#java -cp .;jai_core.jar;jai_codec.jar Blur baby.tiff 3
java -cp .;jai_core.jar;jai_codec.jar Sobel black.tiff 5 bb
#java -cp .;jai_core.jar;jai_codec.jar RunLengthEncoding