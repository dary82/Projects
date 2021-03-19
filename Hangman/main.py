from ui import *

s=Sentences("sentences.txt")
service=ServiceSentence(s)
ui=UI(service)
ui.start()