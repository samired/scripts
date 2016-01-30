import os

def addto_file():
  for i in os.listdir("./"):
    os.rename(i, "DE - "+i)


def rearrange_file():
  #order = [0,1,2,3,12,13,14,15,16,17,18,19,20,21,4,5,6,7,8,9,10,11,22,23,24,25]
  for filename in os.listdir('.'):
    list(filename)
    neworder = [filename[i] for i in order]
    os.rename(filename,"".join(neworder))

def rename_file():
  for filename in os.listdir('.'):
    os.rename(filename, filename.replace('-CAI-', '-'))
  for filename in os.listdir('.'):
    os.rename(filename, filename.replace('CAI', ''))
  for filename in os.listdir('.'):
    os.rename(filename, filename.replace('.pdf', '-CAI.pdf'))
	
	
#this function you can change file names in a directory
#change all the existing file names in a directory to new file names
# eg.  <oldfilename_x.txt> to <001.txt>


def seq_file():
  count = 1
  for fn in os.listdir("."):
    if fn[-4:] == ".jpg":
      os.rename(fn, "%03i.jpg" % count)
      count = count + 1


#rearrange_file()
#rename_file()
#seq_file()

