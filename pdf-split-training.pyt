from pdfsplit import splitPages as sp
import os


def splitz (x, y, name):
    sp ('a.pdf' , [slice (x,y, None)])
    os.rename('a-split.pdf' , name +'.pdf')
    
def training():
	splitz(0,6,"afifi")
	splitz(6,12,"mahfouz")
	splitz(12,18,"mostafa-shaban")
	splitz(18,24,"alfi")
	splitz(24,30,"ramadan")
	splitz(30,36,"idrees")
	
training()   