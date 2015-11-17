from pdfsplit import splitPages as sp
import os

name = "Ahmed Osama"
location = "C:/Users/SamirM1/Desktop/upload/" 


def splitz (x, y, unit, name, location):
    sp (location+'a.pdf' , [slice (x,y, None)])
    os.rename(location+'a-split.pdf' , location+name+'-Unit-'+unit+'.pdf')
    
def uCan():
	splitz(0,1,'Br',name,location)
	splitz(1,6,'1',name,location)
	splitz(6,8,'3',name,location)
	splitz(8,10,'4',name,location)
	splitz(10,12,'6',name,location)
	splitz(12,14,'10',name,location)
	splitz(14,16,'11',name,location)

def eAssess():
	splitz(0,3,'2',name,location)
	splitz(3,23,'5',name,location)
	splitz(23,35,'7',name,location)
	splitz(35,48,'8E1',name,location)
	splitz(48,57,'8E2-5',name,location)
	splitz(57,64,'9',name,location)

def uCant():
	splitz(0,1,'Br',name,location)
	splitz(1,8,'1',name,location)
	splitz(8,13,'3',name,location)
	splitz(13,19,'4',name,location)
	splitz(19,23,'6',name,location)
	splitz(23,26,'10',name,location)
	splitz(26,30,'11',name,location)
	#splitz(29,39,'8E2-5',name,location)

def eAssesst():
	#splitz(19,3,'2',name,location)
	splitz(0,19,'5',name,location)
	splitz(19,30,'7',name,location)
	splitz(30,43,'8E1',name,location)
	#splitz(48,57,'8E2-5',name,location)
   splitz(44,52,'9',name,location)

uCant()
#eAssess()
    