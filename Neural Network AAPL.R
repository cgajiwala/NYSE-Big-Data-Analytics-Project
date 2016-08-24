library("neuralnet")
library("nnet")

install.packages("nnet")
install.packages("neuralnet")

dataset <- read.csv("Apple_NYSE_NN.csv")


head(dataset)

## extract a set to train the NN
trainset <- dataset[1:56, ]


## select the test set
testset <- dataset[57:141, ]



## build the neural network (NN)

#nn.NetPrice <- neuralnet(Adj.Close ~ High+ Low + Close + Open, trainset, hidden = 1, lifesign = "minimal", linear.output = TRUE)

nn.NetPrice <- neuralnet(Buy ~ Volume +Open+Adj.Close, trainset, hidden = 2, lifesign = "minimal", linear.output = FALSE, threshold = 0.1)
nn.NetPrice
# The decay parameter is there to ensure that the model does not overtrain.
#One way we can check the output of the classifier is the confusion matrix. This is the matrix of predicted class versus the actual class. 

summary(nn.NetPrice)

print(nn.NetPrice)

plot(nn.NetPrice)


#Datapred =  prediction(nn.NetPrice, testset)


columns <- c("Volume","Open","Adj.Close")
covariate <- subset(testset, select = columns)



ab <- compute(nn.NetPrice, covariate, rep =1)

ab


covariate_train <- subset(trainset, select = columns)

#Predicted values for neural network

#compute(nn.NetPrice, covariate_train, rep =1)



#Confusion Matrix



table(trainset$Buy,ab)

#table(dataset$Buy,predict(nn.netprice,newdata=testset,type="class"))


#Neural network's predicted output


results <- data.frame(actual= testset$Buy, prediction = ab$net.result)

results

#The size argument specifies how many nodes to have in the hidden layer, skip indicates
#that the input layer has a direct connection to the output layer and linout specifies the
#simple identity activation function.
#The output from summary gives us the detail of the neural network. i1, i2 and i3
#are the input nodes (so close, volume and open respectively); o is the output node (so
#Adj. close); and b is the bias.

#Neural networks do not, by default, pick up any interactions between variables in the
#data. However, you can add variable interactions manually.

#predict_test <- (a,testset,type='class')



#table(data$trainset$Adj.Close,predict(a,newdata = data$trainset,type = "class"))


plot(nn.NetPrice, rep = "best")


plot(nn.NetPrice)


