from pdfsplit import splitPages as sp
import os

name = "Ahmed Ali"
#location = "C:\\Users\\SamirM1\\Desktop\\" 
location = "C:\\Work\\CAP\\CAP - Scan\\complete\\SA - Ahmed Ali\\"

#special function to split Briefing form
def split_br (x, y, unit, name, location):
    sp (location+'a.pdf' , [slice (x,y, None)])
    os.rename(location+'a-split.pdf' , location+name+"-"+unit+'.pdf')
	
def splitz (x, y, unit, name, location):
    sp (location+'a.pdf' , [slice (x,y, None)])
    os.rename(location+'a-split.pdf' , location+name+'-Unit-'+unit+'.pdf')

def uCan():
    split_br(0,1,'Br',name,location)
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
    split_br(0,1,'Br',name,location)
    splitz(1,5,'1',name,location)
    splitz(5,8,'3',name,location)
    #splitz(8,10,'4',name,location)
    splitz(8,10,'6',name,location)
    splitz(10,12,'10',name,location)
    splitz(12,15,'11',name,location)

uCan()
#eAssess()

